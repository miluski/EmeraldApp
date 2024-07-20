import { User } from "./User";
import {
  CHANGE_ACCOUNT_BALANCE,
  CHANGE_IS_LOGIN_SUCCESSFULL,
  CHANGE_IS_PASSWORD_VALID,
  CHANGE_IS_USERNAME_VALID,
  CHANGE_PASSWORD,
  CHANGE_USERNAME,
} from "./UserActionTypes";

const intialState = {
  username: "",
  password: "",
  accountBalance: 0.0,
  isUsernameValid: undefined,
  isPasswordValid: undefined,
  isLoginSuccessfull: undefined,
};

export function userReducer(state = intialState, action: User): User {
  switch (action.type) {
    case CHANGE_USERNAME:
      return {
        ...state,
        username: action.username,
      };
    case CHANGE_PASSWORD:
      return {
        ...state,
        password: action.password,
      };
    case CHANGE_ACCOUNT_BALANCE:
      return {
        ...state,
        accountBalance: action.accountBalance,
      };
    case CHANGE_IS_USERNAME_VALID:
      return {
        ...state,
        isUsernameValid: action.isUsernameValid,
      };
    case CHANGE_IS_PASSWORD_VALID:
      return {
        ...state,
        isPasswordValid: action.isPasswordValid,
      };
    case CHANGE_IS_LOGIN_SUCCESSFULL:
      return {
        ...state,
        isLoginSuccessfull: action.isLoginSuccessfull,
      };
    default:
      return state;
  }
}
