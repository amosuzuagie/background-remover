import { useContext, useEffect } from "react";
import { AppContext } from "../context/AppContext";
import { useSearchParams, useNavigate } from "react-router-dom";

const PaymentDetails = () => {
  const { order, retrieveOrder } = useContext(AppContext);
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const reference = searchParams.get("reference");

  useEffect(() => {
    retrieveOrder(reference);
  }, [reference]);

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-50">
      <div className="max-w-lg w-full mx-auto p-6 shadow-lg rounded-lg bg-white">
        <h1 className="text-2xl font-bold text-green-600 mb-4">
          Payment Successful!
        </h1>
        <p className="text-gray-700 mb-2">Thank you for your payment.</p>

        <div className="mt-4 border-t pt-4">
          <h2 className="font-semibold">Order Details</h2>
          <p>
            <strong>Order ID:</strong> {order?.id}
          </p>
          <p>
            <strong>Entity:</strong> {order?.entity}
          </p>
          <p>
            <strong>Amount:</strong> ₦{(order?.amount / 100).toFixed(2)}
          </p>
          <p>
            <strong>Status:</strong> {order?.status}
          </p>
          <p>
            <strong>Date:</strong>{" "}
            {new Date(order?.created_at).toLocaleString()}
          </p>
          <p>
            <strong>Receipt:</strong> {order?.receipt}
          </p>
        </div>

        {/* Button to go home */}
        <button
          onClick={() => navigate("/")}
          className="mt-6 w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-lg transition duration-200"
        >
          Go to Home
        </button>
      </div>
    </div>
  );
};

export default PaymentDetails;
