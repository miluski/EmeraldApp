import { axiosInstance } from "./axiosInstance";

export async function handleDeleteCampaign(
  id: number,
  navigate: any
): Promise<void> {
  const response = await axiosInstance.delete(`/api/campaignes/${id}`);
  response.status === 200
    ? navigate(0)
    : alert("Wystąpił błąd przy usuwaniu wybranej kampani!");
}
