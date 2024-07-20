import { Provider } from "react-redux";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { combineReducers, legacy_createStore } from "redux";
import { userReducer } from "./utils/userReducer";
import AccessDenied from "./views/AccessDenied";
import { GuardView } from "./views/GuardView";
import LoginView from "./views/LoginView";
import MainPage from "./views/MainPage";
import NotFound from "./views/NotFound";

const browserRouter = createBrowserRouter([
  {
    path: "*",
    element: <NotFound />,
  },
  {
    path: "/RecruitmentTask",
    element: <LoginView />,
  },
  {
    path: "/RecruitmentTask/unauthorized",
    element: <AccessDenied />,
  },
  {
    path: "/RecruitmentTask/main-page",
    element: (
      <GuardView>
        <MainPage />
      </GuardView>
    ),
  },
]);

function App() {
  const reducers = combineReducers({
    userReducer,
  });
  const store = legacy_createStore(reducers);
  return (
    <Provider store={store}>
      <RouterProvider router={browserRouter} />
    </Provider>
  );
}

export default App;
