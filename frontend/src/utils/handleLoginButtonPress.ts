import { Dispatch, UnknownAction } from "redux";
import { axiosInstance } from "./axiosInstance";
import { User } from "./User";
import { CHANGE_IS_LOGIN_SUCCESSFULL } from "./UserActionTypes";
import { validateData } from "./validateData";

export async function handleLoginButtonPress(
  user: User,
  dispatch: Dispatch<UnknownAction>
): Promise<void> {
  const isDataValid = validateData(user, dispatch);
  if (isDataValid) {
    const response = await axiosInstance.post("/api/auth/user/login", user);
    if (response.status === 403) {
      dispatch({
        type: CHANGE_IS_LOGIN_SUCCESSFULL,
        isLoginSuccessfull: false,
      });
    } else {
      dispatch({
        type: CHANGE_IS_LOGIN_SUCCESSFULL,
        isLoginSuccessfull: true,
      });
      localStorage.setItem("isLoggedIn", "true");
      localStorage.setItem("userCredentials", JSON.stringify(response.data));
      window.location.href = "/RecruitmentTask/main-page";
    }
  }
}
