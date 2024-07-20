import { Dispatch, UnknownAction } from "redux";
import { User } from "./User";
import {
    CHANGE_IS_PASSWORD_VALID,
    CHANGE_IS_USERNAME_VALID,
} from "./UserActionTypes";

export const validateData = (
  user: User,
  dispatch: Dispatch<UnknownAction>
): boolean => {
  const usernameRegex = /^[a-zA-Z0-9_.]{3,15}$/;
  const passwordRegex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
  const isUsernameValid = usernameRegex.test(user.username);
  const isPasswordValid = passwordRegex.test(user.password);
  dispatch({
    type: CHANGE_IS_USERNAME_VALID,
    isUsernameValid: isUsernameValid,
  });
  dispatch({
    type: CHANGE_IS_PASSWORD_VALID,
    isPasswordValid: isPasswordValid,
  });
  return isUsernameValid && isPasswordValid;
};
