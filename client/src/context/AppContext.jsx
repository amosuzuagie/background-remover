import { useAuth, useClerk, useUser } from "@clerk/clerk-react";
import axios from "axios";
import { createContext, useState } from "react";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

export const AppContext = createContext(); // managing global state.

const AppProviderProvider = (props) => {
  const backendUrl = import.meta.env.VITE_BACKEND_URL;
  const [credits, setCredits] = useState(false);
  const { getToken } = useAuth();
  const { isSignedIn } = useUser();
  const { openSignIn } = useClerk();
  const [order, setOrder] = useState(null);
  const [image, setImage] = useState(false);
  const [resultImage, setResultImage] = useState(false);
  const navigate = useNavigate();

  const loadUserCredits = async () => {
    try {
      const token = await getToken();
      const response = await axios.get(backendUrl + "/users/credits", {
        headers: { Authorization: `Bearer ${token}` },
      });
      console.log("USER CREDIT: " + response.data.data);
      if (response.data.success) {
        setCredits(response.data.data.credits);
      } else {
        toast.error(response.data.data);
      }
    } catch (error) {
      toast.error("Error loading the credits");
    }
  };

  const retrieveOrder = async (reference) => {
    try {
      const token = await getToken();
      const response = await axios.get(`${backendUrl}/orders/${reference}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (response.data.success) {
        setOrder(response.data.data);
      }
    } catch (error) {
      toast.error("Error retrieving order details.");
    }
  };

  const removeBg = async (selectedImage) => {
    try {
      if (!isSignedIn) return openSignIn();

      //  Enforce ClipDrop file size limit here
      const MAX_FILE_SIZE = 30 * 1024 * 1024; // 30MB
      const ALLOWED_TYPES = ["image/png", "image/jpeg", "image/webp"];

      if (selectedImage.size > MAX_FILE_SIZE) {
        toast.error("Image is too large. Max allowed is 30MB.");
        return;
      }

      if (!ALLOWED_TYPES.includes(selectedImage.type)) {
        toast.error("Invalid image type. Please upload PNG, JPG, or WEBP.");
        return;
      }

      setImage(selectedImage);
      setResultImage(false);
      navigate("/result");

      const token = await getToken();
      const formData = new FormData();
      selectedImage && formData.append("file", selectedImage);
      const { data: base64Image } = await axios.post(
        backendUrl + "/images/remove-background",
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setResultImage(`data:image/png;base64,${base64Image}`);
      setCredits(credits - 1);
    } catch (error) {
      console.log(error);
      toast.error("there was error while removing the background.");
    }
  };

  const contextValue = {
    backendUrl,
    credits,
    setCredits,
    loadUserCredits,
    image,
    setImage,
    resultImage,
    setResultImage,
    removeBg,
    order,
    retrieveOrder,
  };

  return (
    <AppContext.Provider value={contextValue}>
      {props.children}
    </AppContext.Provider>
  );
};

export default AppProviderProvider;
