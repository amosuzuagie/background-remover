import { useAuth } from "@clerk/clerk-react";
import axios from "axios";
import { createContext, useState } from "react";
import toast from "react-hot-toast";

export const AppContext = createContext(); // managing global state.

const AppProviderProvider = (props) => {
  const backendUrl = import.meta.env.VITE_BACKEND_URL;
  const [credits, setCredits] = useState(false);
  const { getToken } = useAuth();

  const loadUserCredits = async () => {
    try {
      const token = await getToken();
      const response = await axios.get(backendUrl + "/users/credits", {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (response.data.success) {
        setCredits(response.data.data.credits);
      } else {
        toast.error(response.data.data);
      }
    } catch (error) {
      toast.error("Error loading the credits");
    }
  };

  const contextValue = {
    backendUrl,
    credits,
    setCredits,
    loadUserCredits,
  };

  return (
    <AppContext.Provider value={contextValue}>
      {props.children}
    </AppContext.Provider>
  );
};

export default AppProviderProvider;
