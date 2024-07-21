import axios, {
  AxiosError,
  AxiosResponse,
  InternalAxiosRequestConfig,
} from "axios";

export const axiosInstance = axios.create({
  baseURL: "https://emerald-app-backend-5c109de0be17.herokuapp.com/",
  headers: {
    "Content-Type": "application/json",
  }
});

axiosInstance.interceptors.request.use(
  (axiosRequestConfig: InternalAxiosRequestConfig<any>) => {
    const accessToken = localStorage.getItem("accessToken") ?? "";
    accessToken.replace("\"", "");
    accessToken != ""
      ? (axiosRequestConfig.headers.Authorization = `Bearer ${accessToken}`)
      : null;
    return axiosRequestConfig;
  },
  (error: AxiosError) => Promise.reject(error)
);

axiosInstance.interceptors.response.use(
  (axiosResponse: AxiosResponse<any>) => {
    return axiosResponse;
  },
  async (error: AxiosError) => {
    const { config, response } = error as AxiosError<any>;
    if (response && response.status === 401) {
      const accessToken = localStorage.getItem("accessToken") ?? "";
      accessToken.replace("\"", "");
      const { data, status } = await axiosInstance.post(
        "/api/auth/tokens/refresh",
        accessToken
      );
      localStorage.setItem("isLoggedIn", String(status === 200));
      localStorage.setItem("accessToken", data);
      return config != undefined
        ? new Promise((resolve: Function) => {
            config.headers["Authorization"] = data;
            resolve(axiosInstance(config as InternalAxiosRequestConfig<any>));
          })
        : Promise.reject(error);
    } else if (response && response.status === 403) {
      return response;
    }
  }
);
