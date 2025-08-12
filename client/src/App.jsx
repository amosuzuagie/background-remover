import { Route, Routes } from "react-router-dom";
import Footer from "./components/Footer";
import Menubar from "./components/Menubar";
import Home from "./pages/Home";
import { Toaster } from "react-hot-toast";
import UserSyncHandler from "./components/UserSyncHandler";
import { RedirectToSignIn, SignedIn, SignedOut } from "@clerk/clerk-react";
import Result from "./pages/Result";
import BuyCredits from "./pages/BuyCredits";
import PaymentDetails from "./pages/PaymentDetails";

function App() {
  return (
    <>
      <UserSyncHandler />
      <Menubar />
      <Toaster />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/pricing" element={<BuyCredits />} />
        <Route path="/payment_details" element={<PaymentDetails />} />
        <Route
          path="/result"
          element={
            <>
              <SignedIn>
                <Result />
              </SignedIn>
              <SignedOut>
                <RedirectToSignIn />
              </SignedOut>
            </>
          }
        />
      </Routes>
      <Footer />
    </>
  );
}

export default App;
