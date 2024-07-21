import { axiosInstance } from "./axiosInstance";
import { Campaign } from "./Campaign";

export async function getUserCampaignes(): Promise<Campaign[] | null> {
  const { id } = JSON.parse(
    localStorage.getItem("userCredentials") ?? '{"id": -1}'
  );
  const response = await axiosInstance.get(`/api/campaignes/user/${id}`);
  return response.status === 200 ? response.data : null;
}
