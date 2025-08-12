import { useAuth, useClerk } from "@clerk/clerk-react";
import { plans } from "../assets/assets";
import { placeOrder } from "../service/OrderService";
import { useContext } from "react";
import { AppContext } from "../context/AppContext";

const BuyCredits = () => {
  const { isSignedIn, getToken } = useAuth();
  const { openSignIn } = useClerk();
  const { backendUrl, loadUserCredits } = useContext(AppContext);

  const handleOrder = (planId) => {
    if (!isSignedIn) {
      return openSignIn();
    }

    placeOrder({
      planId,
      getToken,
      onSuccess: () => {
        loadUserCredits();
      },
      backendUrl,
    });
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 px-4">
      <div className="w-full max-w-7xl">
        {/** Section Title */}
        <div className="mb-12 text-center">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-900">
            Choose your perfect package
          </h2>
          <p className="mx-auto mt-4 max-w-2xl text-gray-800">
            Select from our carefully curated photography packages designed to
            meet your specific needs and budgets.
          </p>
        </div>

        {/** Section Body */}
        <div className="grid gap-8 md:grid-cols-2 lg:grid-cols-3">
          {plans.map((plan) => (
            <div
              key={plan.id}
              className={`relative pt-6 p-6 ${
                plan.popular
                  ? "backdrop-blur-lg rounded-2xl"
                  : "border border-gray-800 rounded-xl"
              } bg-[#1a1a1a] hover:transform hover:translate-y-2 transition-all duration-300`}
            >
              {plan.popular && (
                <div className="absolute -top-4 left-1/2 -translate-x-1/2 rounded-full bg-purple-600 px-3 py-1 text-white text-sm font-semibold">
                  Most popular
                </div>
              )}
              <div className="text-center p-6">
                <h3 className="text-2xl font-bold text-white">{plan.name}</h3>
                <div className="mt-4 text-center">
                  <span className="text-4xl text-violet-400 font-bold">
                    &#8358;{plan.price}
                  </span>
                </div>
              </div>
              <div className="px-4 pb-8">
                <ul className="mb-8 space-y-4">
                  <li className="flex items-center text-white">
                    {plan.credits}
                  </li>
                  <li className="flex items-center text-white">
                    {plan.description}
                  </li>
                </ul>
                <button
                  onClick={() => handleOrder(plan.id)}
                  className="w-full py-3 px-6 text-center text-white font-semibold rounded-full
                bg-gradient-to-r from-purple-500 to-indigo-500 shadow-lg hover:from-purple-700 hover:to-indigo-700 transition duration-300 ease-in-out transform hover:scale-105 cursor-pointer"
                >
                  Choose plan
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default BuyCredits;
