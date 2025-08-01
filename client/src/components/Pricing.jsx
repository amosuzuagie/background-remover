import { plans } from "../assets/assets";

const Pricing = () => {
  return (
    <div className="py-10 md:px-20 lg:px-20">
      <div className="container mx-auto px-4">
        {/** Section Title */}
        <div className="mb-12 text-center">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-12 text-center">
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
              className={`relative pt-6 p-6 ${
                plan.popular
                  ? "backdrop-blur-lg rounded-2xl"
                  : "border-gray-800 rounded-xl"
              } bg-[#1a1a1a] hover:transform hover:translate-y-2 transition-all duration-300`}
              key={plan.id}
            >
              {plan.popular && (
                <div className="absolute -top-4 left-1/2 -translate-x-1/2 rounded-full bg-purple-600 px-3 py-1 text-white text-sm font-semibold">
                  Most popular
                </div>
              )}
              <div className="text-center p-6">
                <h3 className="text-2xl font-bold text-white ">{plan.name}</h3>
                <div className="mt-4 text-center">
                  <span className="text-4xl text-violet-400 font-bold">
                    &#8358;{plan.price}
                  </span>
                </div>
              </div>
              <div className="px-4 pb-8">
                <ul className="mb-8 space-y-4">
                  <li className=" flex items-center text-white">
                    {plan.credits}
                  </li>
                  <li className=" flex items-center text-white">
                    {plan.description}
                  </li>
                </ul>
                <button
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

export default Pricing;
