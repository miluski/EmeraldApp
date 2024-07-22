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
  await axiosInstance.get("/api/auth/user/logout");
  localStorage.removeItem("isLoggedIn");
  localStorage.removeItem("userCredentials");
  localStorage.removeItem("accessToken");
  dispatch({
    type: CHANGE_IS_LOGOUT_ATTEMPT_STARTED,
    isLogoutAttemptStarted: false,
  });
  navigate("/");
}
