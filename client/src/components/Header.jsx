import { useContext } from "react";
import { assets } from "../assets/assets";
import { AppContext } from "../context/AppContext";

const Header = () => {
  const { removeBg } = useContext(AppContext);
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 gap-12 items-center mb-16">
      {/** Left side: Video banner */}
      <div className="order-2 md:order-1 flex justify-center">
        <div className="shadow-[0_25px_50px_-12px_rgba(0,0,0,0.15)] rounded-3xl overflow-hidden">
          <video
            className="w-full max-w-[400px] h-auto object-cover"
            src={assets.video}
            autoPlay
            loop
            muted
          />
        </div>
      </div>
      {/** Right side: Text content */}
      <div className="order-1 md:order-2">
        <h1 className="text-4xl md:text-5xl font-bold text-gray-900 mb-6 leading-tight">
          The fastest <span className="text-indigo-700">background eraser</span>
        </h1>
        <p className="text-gray-600 mb-8 text-lg leading-relaxed">
          Easily transform your photo with our background remover app! Make your
          subject stand out by creating a transparent background — perfect for
          placing it into any design. Try it now and give your image a whole new
          setting!
        </p>
        <div>
          <input
            type="file"
            accept="image"
            id="upload1"
            hidden
            onChange={(e) => removeBg(e.target.files[0])}
          />
          <label
            className="bg-black font-medium text-white px-8 py-4 rounded-full hover:opacity-90 transition-transform hover:scale-105 text-lg"
            htmlFor="upload1"
          >
            Upload your image
          </label>
        </div>
      </div>
    </div>
  );
};

export default Header;
