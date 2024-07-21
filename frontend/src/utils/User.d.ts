import { Reducer } from "redux";

export type User = {
    type?: string;
    username: string;
    password: string;
    accountBalance?: number;
    isUsernameValid?: boolean;
    isPasswordValid?: boolean;
    isLoginSuccessfull?: boolean;
    isLoginAttemptStarted?: boolean;
    isLogoutAttemptStarted?: boolean;
    userReducer?: Reducer<User, User>;
}