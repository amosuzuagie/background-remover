import { useAuth, useUser } from "@clerk/clerk-react";
import { useContext, useEffect, useState } from "react";
import { AppContext } from "../context/AppContext";
import axios from "axios";
import toast from "react-hot-toast";

const UserSyncHandler = () => {
  const { isLoaded, isSignedIn, getToken } = useAuth();
  const [synced, setSynced] = useState(false);
  const { backendUrl, loadUserCredits } = useContext(AppContext);
  const { user } = useUser();

  useEffect(() => {
    const saveUser = async () => {
      if (!isLoaded || !isSignedIn || synced) {
        return;
      }

      try {
        const token = await getToken();
        const userData = {
          clerkId: user.id,
          email: user.primaryEmailAddress.emailAddress,
          firstName: user.firstName,
          lastName: user.lastName,
          photoUrl: user.imageUrl,
        };

        // console.log("User Data: ", userData);

        await axios.post(backendUrl + "/users", userData, {
          headers: { Authorization: `Bearer ${token}` },
        });

        setSynced(true);
        await loadUserCredits();
      } catch (error) {
        console.error("User sync failed", error);
        toast.error("Unable to create account. Please try again.");
      }
    };

    saveUser();
  }, [isLoaded, isSignedIn, getToken, user, synced]);

  return null;
};

export default UserSyncHandler;
