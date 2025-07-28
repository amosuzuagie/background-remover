import BgRemovalSteps from "../components/BgRemovalSteps";
import BgSlider from "../components/BgSlider";
import Header from "../components/Header";
import Pricing from "../components/Pricing";
import Testimonial from "../components/Testimonials";
import TryNow from "../components/TryNow";

const Home = () => {
  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16 font-['Outfit']">
      <Header />
      <BgRemovalSteps />
      <BgSlider />
      <Pricing />
      <Testimonial />
      <TryNow />
    </div>
  );
};

export default Home;
