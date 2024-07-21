import { Provider } from "react-redux";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { combineReducers, legacy_createStore } from "redux";
import { campaignReducer } from "./utils/campaignReducer";
import { userReducer } from "./utils/userReducer";
import AccessDenied from "./views/AccessDenied";
import { GuardView } from "./views/GuardView";
import LoginView from "./views/LoginView";
import MainPage from "./views/MainPage";
import NotFound from "./views/NotFound";

const browserRouter = createBrowserRouter([
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
    campaignReducer,
  });
  const store = legacy_createStore(reducers);
  return (
    <Provider store={store}>
      <RouterProvider router={browserRouter} />
    </Provider>
  );
}

export default App;
