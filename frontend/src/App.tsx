import { Provider } from "react-redux";
import { createHashRouter, RouterProvider } from "react-router-dom";
import { combineReducers, legacy_createStore } from "redux";
import { userReducer } from "./utils/userReducer";
import AccessDenied from "./views/AccessDenied";
import { GuardView } from "./views/GuardView";
import LoginView from "./views/LoginView";
import MainPage from "./views/MainPage";
import NotFound from "./views/NotFound";

const hashRouter = createHashRouter([
  {
    path: "/",
    children: [
      { index: true, element: <LoginView /> },
      { path: "unauthorized", element: <AccessDenied /> },
      {
        path: "main-page",
        element: (
          <GuardView>
            <MainPage />
          </GuardView>
        ),
      },
      { path: "*", element: <NotFound /> },
    ],
  },
]);

function App() {
  const reducers = combineReducers({
    userReducer,
  });
  const store = legacy_createStore(reducers);
  return (
    <Provider store={store}>
      <RouterProvider router={hashRouter} />
    </Provider>
  );
}

export default App;
