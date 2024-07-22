import AddBoxIcon from "@mui/icons-material/AddBox";
import EditNoteIcon from "@mui/icons-material/EditNote";
import TrendingUpIcon from "@mui/icons-material/TrendingUp";
import {
  AppBar,
  Box,
  Button,
  CircularProgress,
  CssBaseline,
  Divider,
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Paper,
  Toolbar,
  Typography,
} from "@mui/material";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Campaign } from "../utils/Campaign";
import { handleLogoutButtonPress } from "../utils/handleLogoutButtonPress";
import { User } from "../utils/User";
import { CHANGE_SELECTED_MAIN_PAGE_INDEX } from "../utils/UserActionTypes";
import AddCampaignView from "./AddCampaignView";
import EditCampaignView from "./EditCampaignView";
import MyCampaignesView from "./MyCampaignesView";

export default function MainPage() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { isLogoutAttemptStarted, selectedMainPageIndex } = useSelector(
    (state: User) => state.userReducer as unknown as User
  );
  const { isCampaignAdded } = useSelector(
    (state: Campaign) => state.campaignReducer as unknown as Campaign
  );
  const { username, accountBalance } = JSON.parse(
    localStorage.getItem("userCredentials") ??
      '{"username": "", "accountBalance": 0}'
  );
  const balanceFormatted = formatBalance(accountBalance);
  const balanceStyle = { color: accountBalance > 0 ? "green" : "inherit" };

  const handleListItemClick = (index: number) => {
    dispatch({
      type: CHANGE_SELECTED_MAIN_PAGE_INDEX,
      selectedMainPageIndex: index,
    });
  };

  function formatBalance(balance: string) {
    const roundedBalance = parseFloat(balance).toFixed(2);
    return roundedBalance.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
  }

  useEffect(() => {}, [isCampaignAdded]);

  return (
    <Box sx={{ display: "flex", height: "100vh" }}>
      <CssBaseline />
      <AppBar
        sx={{ width: `calc(100% - 240px)`, ml: `240px`, bgcolor: "#2f7d31" }}
      >
        <Toolbar sx={{ justifyContent: "center" }}>
          <Typography variant="h6" noWrap component="div">
            Emerald App
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        sx={{
          width: 240,
          flexShrink: 0,
          "& .MuiDrawer-paper": {
            width: 240,
            boxSizing: "border-box",
            backgroundColor: "#f4f4f4",
          },
        }}
        variant="permanent"
        anchor="left"
      >
        <Paper
          elevation={3}
          sx={{ margin: "1rem", padding: "1rem", backgroundColor: "#e8eaf6" }}
        >
          <Typography variant="h6" component="p" gutterBottom>
            Hi, {username}!
          </Typography>
          <Typography variant="body1">
            Balance:{" "}
            <span style={balanceStyle} className="font-bold">
              {balanceFormatted}
            </span>{" "}
            PLN
          </Typography>
        </Paper>
        <Divider />
        <List>
          {["My campaignes", "Add campaign", "Edit campaign"].map(
            (text: string, index: number) => (
              <ListItem key={index} disablePadding>
                <ListItemButton
                  selected={selectedMainPageIndex === index}
                  onClick={() => index !== 2 && handleListItemClick(index)}
                  sx={{
                    "&.Mui-selected": {
                      backgroundColor: "#dedede",
                      color: "primary.main",
                      "&:hover": {
                        backgroundColor: "#c0c0c0",
                      },
                    },
                    "&:hover": {
                      backgroundColor: "#e0e0e0",
                    },
                    ...(index === 2 && {
                      pointerEvents: "none",
                      color: "action.disabled",
                    }),
                  }}
                >
                  <ListItemIcon>
                    {index === 0 ? (
                      <TrendingUpIcon />
                    ) : index === 1 ? (
                      <AddBoxIcon />
                    ) : (
                      <EditNoteIcon />
                    )}
                  </ListItemIcon>
                  <ListItemText
                    primary={<span style={{ color: "gray" }}>{text}</span>}
                  />
                </ListItemButton>
              </ListItem>
            )
          )}
        </List>
        <div className="flex justify-center top-full w-[100%] my-10">
          <Button
            variant="contained"
            color="success"
            className="w-[50%]"
            onClick={async () => handleLogoutButtonPress(navigate, dispatch)}
          >
            Logout
          </Button>
        </div>
      </Drawer>
      <Box
        component="main"
        sx={{ flexGrow: 1, bgcolor: "background.default", p: 3 }}
      >
        <Toolbar />
        {selectedMainPageIndex === 0 ? (
          <MyCampaignesView />
        ) : selectedMainPageIndex === 1 ? (
          <AddCampaignView />
        ) : (
          <EditCampaignView />
        )}
      </Box>
      {isLogoutAttemptStarted && (
        <div className="absolute top-0 left-0 w-full h-full bg-white bg-opacity-50 z-50">
          <CircularProgress className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2" />
        </div>
      )}
    </Box>
  );
}
