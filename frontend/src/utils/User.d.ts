import { Reducer } from "redux";

export type User = {
    type?: string;
    username: string;
    password: string;
    accountBalance?: number;
    isUsernameValid?: boolean;
    isPasswordValid?: boolean;
    isLoginSuccessfull?: boolean;
    userReducer?: Reducer<User, User>;
}