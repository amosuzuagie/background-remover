import { assets, FOOTER_CONTENT } from "../assets/assets";
import { FiGithub, FiLinkedin, FiTwitter } from "react-icons/fi";

const Footer = () => {
  return (
    <footer className="bg-gray-300 flex items-center justify-between gap-4 px-4 lg:px-44 py-3">
      <img width={32} src={assets.logo} alt="logo" />
      <p className="flex-1 border-l border-gray-100 max-sm:hidden">
        &copy; {new Date().getFullYear()} @amosuzuagie | All rights reserved.
      </p>
      {/* <div className="flex gap-3">
        {FOOTER_CONTENT.map((item) => (
          <a href={item.url} key={item} target="_blank">
            <img
              src={item.logo}
              alt="logo"
              width={32}
              rel="noopener noreferrer"
            />
          </a>
        ))}
      </div> */}

      <a
        href="https://github.com/amosuzuagie"
        target="_blank"
        rel="noopener noreferrer"
        className="text-indigo-700 hover:text-gray-700 
            dark:hover:text-violet-400 transition-colors duration-300"
      >
        <FiGithub className="w-5 h-5" />
      </a>
      <a
        href="https://www.linkedin.com/in/amos-uzuagie-7174731a6/"
        target="_blank"
        rel="noopener noreferrer"
        className="text-indigo-700 hover:text-gray-700 
            dark:hover:text-violet-400 transition-colors duration-300"
      >
        <FiLinkedin className="w-5 h-5" />
      </a>
      <a
        href="https://x.com/amosuzuagie"
        target="_blank"
        rel="noopener noreferrer"
        className="text-indigo-700 hover:text-gray-700 
            dark:hover:text-violet-400 transition-colors duration-300"
      >
        <FiTwitter className="w-5 h-5" />
      </a>
      <p className="text-center text-gray-700 font-medium"></p>
    </footer>
  );
};
export default Footer;
