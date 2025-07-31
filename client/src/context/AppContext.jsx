import { createContext } from "react";

export const AppContext = createContext(); // managing global state.

const AppProviderProvider = (props) => {
  const backendUrl = import.meta.env.VITE_BACKEND_URL;
  // const PUBLISHABLE_KEY = import.meta.env.VITE_CLERK_PUBLISHABLE_KEY;

  const contextValue = {
    backendUrl,
  };

  return (
    <AppContext.Provider value={contextValue}>
      {props.children}
    </AppContext.Provider>
  );
};

export default AppProviderProvider;
