import logo from "./logo.png";
import people from "./people.png";
import people_org from "./people-org.png";
import video from "./home-page-banner.mp4";

export const assets = {
  logo,
  video,
  people,
  people_org,
};

export const steps = [
  {
    step: "Step 1",
    title: "Select an Image",
    description: `First, choose the image you want to remove background from by clicking
        on "Upload your image" Your image format can PNG or JPG
        We support all image dimensions.`,
  },
  {
    step: "Step 2",
    title: "Let magic remove the backgrond",
    description: `Our tool automatically removes the backgrond from your image. Next, you
        can choose a backgrond color. Our most popular options are white or
        transperent backgrond, but you can choose a color of your choice.`,
  },
  {
    step: "Step 3",
    title: "Download your image",
    description: `After selecting a new backgrond color, download your photo and you are
        done! You can also save your pictures in our Photoroom App by creating an
        account for free.`,
  },
];

export const categories = ["People", "Products", "Animals", "Cars", "Graphics"];

export const plans = [
  {
    id: "Basic",
    name: "Basic Package",
    price: 499,
    credits: "100 credits",
    description: "Best for personal use",
    popular: false,
  },
  {
    id: "Premium",
    name: "Premium Package",
    price: 899,
    credits: "250 credits",
    description: "Best for business use",
    popular: true,
  },
  {
    id: "Ultimate",
    name: "Ultimate Package",
    price: 1499,
    credits: "1000 credits",
    description: "Best for enterprice use",
    popular: false,
  },
];

export const testimonials = [
  {
    id: 1,
    quote:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    author: "Edie Marphis",
    handle: "@_marphistech",
  },
  {
    id: 1,
    quote:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    author: "John Doe",
    handle: "@_Doetech",
  },
  {
    id: 1,
    quote:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    author: "Mariam Ibrahim",
    handle: "@_mariamtech",
  },
];

export const FOOTER_CONTENT = [
  {
    url: "https://github.com/amosuzuagie",
    logo: "https://img.icons8.com/fluent/30/000000/facebook-new.png",
  },
  {
    url: "https://linkedin.com",
    logo: "https://img.icons8.com/fluent/30/000000/facebook-new.png",
  },
  {
    url: "https://facebook.com",
    logo: "https://img.icons8.com/fluent/30/000000/facebook-new.png",
  },
  {
    url: "https://facebook.com",
    logo: "https://img.icons8.com/fluent/30/000000/facebook-new.png",
  },
];
