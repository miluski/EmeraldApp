import { axiosInstance } from "./axiosInstance";

export async function handleLogoutButtonPress(navigate: any): Promise<void> {
  const response = await axiosInstance.get("/api/auth/user/logout");
  localStorage.removeItem("isLoggedIn");
  localStorage.removeItem("userCredentials");
  response.status === 200
    ? navigate("/")
    : alert("Wystąpił błąd przy próbie wylogowania. Spróbuj ponownie później!");
}
