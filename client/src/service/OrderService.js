import axios from "axios";

export const placeOrder = async ({ planId, getToken, backendUrl }) => {
  try {
    const token = await getToken();
    const response = await axios.post(
      `${backendUrl}/orders?planId=${planId}`,
      {},
      { headers: { Authorization: `Bearer ${token}` } }
    );

    if (response.status === 201) {
      window.location.href = response.data.data.payment_link;
    }
  } catch (error) {
    toast.error(error.message);
  }
};
