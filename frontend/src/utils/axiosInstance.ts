import axios, {
  AxiosError,
  AxiosResponse,
  InternalAxiosRequestConfig,
} from "axios";

export const axiosInstance = axios.create({
  baseURL: "/server",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

axiosInstance.interceptors.response.use(
  (axiosResponse: AxiosResponse<any>) => {
    return axiosResponse;
  },
  async (error: AxiosError) => {
    const { config, response } = error as AxiosError<any>;
    if (response && response.status === 401) {
      const { status } = await axiosInstance.get("/api/auth/tokens/refresh");
      localStorage.setItem("isLoggedIn", String(status === 200));
      return new Promise((resolve: Function) =>
        resolve(axiosInstance(config as InternalAxiosRequestConfig<any>))
      );
    } else if (response && response.status === 403) {
      return response;
    }
  }
);
