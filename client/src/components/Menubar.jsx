import { useContext, useState } from "react";
import { assets } from "../assets/assets";
import { Menu, X } from "lucide-react";
import { Link } from "react-router-dom";
import {
  SignedIn,
  SignedOut,
  useClerk,
  UserButton,
  useUser,
} from "@clerk/clerk-react";
import { AppContext } from "../context/AppContext";

const Menubar = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  const { openSignIn, openSignUp } = useClerk();
  const { credits } = useContext(AppContext);
  const { user } = useUser();

  const openRegister = () => {
    setMenuOpen(false);
    openSignUp({});
  };

  const openLogin = () => {
    setMenuOpen(false);
    openSignIn({});
  };

  return (
    <nav className="fixed top-0 left-0 w-full z-50 bg-gray-300 px-8 py-4 flex justify-between items-center">
      {/** Left side: logo + text */}
      <Link className="flex items-center space-x-2" to="/">
        <img
          className="h-8 w-8 object-contain cursor-pointer"
          src={assets.logo}
          alt="logo"
        />
        <span className="text-2xl font-semibold text-indigo-700 cursor-pointer">
          zap.<span className="text-gray-600 cursor-pointer">bg</span>
        </span>
      </Link>

      {/** Right side: Action Buttons */}
      <SignedOut>
        <div className="hidden md:flex items-center space-x-4">
          <button
            onClick={openLogin}
            className="text-gray-700 hover:text-blue-700 font-medium"
          >
            Login
          </button>
          <button
            onClick={openRegister}
            className="bg-gray-100 hover:bg-gray-400 text-gray-700 font-medium px-4 py-2 rounded-full transition-all"
          >
            Sign up
          </button>
        </div>
      </SignedOut>
      <SignedIn>
        <div className="max-sm:hidden flex items-center gap-2 sm:gap-3">
          <button className="flex items-center gap-2 bg-blue-100 px-4 sm:px-5 py-1.5 sm:py-2.5 rounded-full hover:scale-105 transition-all duration-500 cursor-pointer">
            <img src={assets.credit} alt="..." height={24} width={24} />
            <p className="text-xs sm:text-sm font-medium text-gray-600">
              Credits: {credits}
            </p>
          </button>
          <p className="text-gray-600 ">Hi, {user?.fullName}</p>
        </div>
        <div className="max-sm:hidden">
          <UserButton />
        </div>
      </SignedIn>

      {/** Mobile hamburger */}
      <div className="flex md:hidden">
        <button onClick={() => setMenuOpen(!menuOpen)}>
          {menuOpen ? <X size={28} /> : <Menu size={28} />}
        </button>
      </div>

      {/** Mobile Menu */}
      {menuOpen && (
        <div className="absolute top-16 right-8 bg-white shadow-md flex flex-col space-y-4 w-40 p-4">
          <SignedOut>
            <button
              onClick={openLogin}
              className="text-gray-700 hover:text-blue-700 font-medium"
            >
              Login
            </button>
            <button
              onClick={openRegister}
              className="bg-gray-100 text-gray-700 font-medium px-4 py-2 rounded-full text-center"
            >
              Sign up
            </button>
          </SignedOut>
          <SignedIn>
            <div className="flex items-center gap-2 sm:gap-3">
              <button className="flex items-center gap-2 bg-blue-100 px-4 py-1.5 sm:py-2.5 rounded-full hover:">
                <img src={assets.credit} alt="..." height={24} width={24} />
                <p className="text-xs sm:text-sm font-medium text-gray-600">
                  Credits: {credits}
                </p>
              </button>
            </div>
            <UserButton />
          </SignedIn>
        </div>
      )}
    </nav>
  );
};

export default Menubar;
