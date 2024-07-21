import { Navigate } from "react-router-dom";

export const GuardView = ({ children }: { children: React.ReactElement }) => {
  const isLoggedIn = Boolean(localStorage.getItem("isLoggedIn") ?? false);
  return isLoggedIn ? (
    children
  ) : (
    <Navigate to="/unauthorized" />
  );
};
