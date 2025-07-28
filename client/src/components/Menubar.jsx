import { useState } from "react";
import { assets } from "../assets/assets";
import { Menu, X } from "lucide-react";

const Menubar = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  return (
    <nav className="bg-white px-8 py-4 flex justify-between items-center">
      {/** Left side: logo + text */}
      <div className="flex items-center space-x-2">
        <img
          className="h-8 w-8 object-contain cursor-pointer"
          src={assets.logo}
          alt="logo"
        />
        <span className="text-2xl font-semibold text-indigo-700 cursor-pointer">
          remove.<span className="text-gray-400 cursor-pointer">bg</span>
        </span>
      </div>

      {/** Right side: Action Buttons */}
      <div className="hidden md:flex items-center space-x-4">
        <button className="text-gray-700 hover:text-blue-700 font-medium">
          Login
        </button>
        <button className="bg-gray-100 hover:bg-gray-400 text-gray-700 font-medium px-4 py-2 rounded-full transition-all">
          Sign up
        </button>
      </div>

      {/** Mobile hamburger */}
      <div className="flex md:hidden">
        <button onClick={() => setMenuOpen(!menuOpen)}>
          {menuOpen ? <X size={28} /> : <Menu size={28} />}
        </button>
      </div>

      {/** Mobile Menu */}
      {menuOpen && (
        <div className="absolute top-16 right-8 bg-white shadow-md flex flex-col space-y-4 w-40 p-4">
          <button className="text-gray-700 hover:text-blue-700 font-medium">
            Login
          </button>
          <button className="bg-gray-100 text-gray-700 font-medium px-4 py-2 rounded-full text-center">
            Sign up
          </button>
        </div>
      )}
    </nav>
  );
};

export default Menubar;
