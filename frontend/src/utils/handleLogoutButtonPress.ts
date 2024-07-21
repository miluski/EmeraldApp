import { Dispatch, UnknownAction } from "redux";
import { axiosInstance } from "./axiosInstance";
import { CHANGE_IS_LOGOUT_ATTEMPT_STARTED } from "./UserActionTypes";

export async function handleLogoutButtonPress(
  navigate: any,
  dispatch: Dispatch<UnknownAction>
): Promise<void> {
  dispatch({
    type: CHANGE_IS_LOGOUT_ATTEMPT_STARTED,
    isLogoutAttemptStarted: true,
  });
  const response = await axiosInstance.get("/api/auth/user/logout");
  localStorage.removeItem("isLoggedIn");
  localStorage.removeItem("userCredentials");
  localStorage.removeItem("accessToken");
  dispatch({
    type: CHANGE_IS_LOGOUT_ATTEMPT_STARTED,
    isLogoutAttemptStarted: false,
  });
  response.status === 200
    ? navigate("/")
    : alert("Wystąpił błąd przy próbie wylogowania. Spróbuj ponownie później!");
}
