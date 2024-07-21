import { Button } from "@mui/material";
import Paper from "@mui/material/Paper";
import { styled } from "@mui/material/styles";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import { useEffect, useState } from "react";
import { getUserCampaignes } from "../utils/getUserCampaignes";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.success.light,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));

function createData(
  id: number,
  campaignName: string,
  keywords: string[],
  bidAmount: number,
  campaignFund: number,
  status: "on" | "off",
  town: string,
  radius: number
) {
  return {
    id,
    campaignName,
    keywords,
    bidAmount,
    campaignFund,
    status,
    town,
    radius,
  };
}

export default function MyCampaignesView() {
  const [rows, setRows] = useState<any[]>([]);
  useEffect(() => {
    (async () => {
      const campaignesArray = await getUserCampaignes();
      if (campaignesArray != null && campaignesArray.length > 0) {
        const transformedRows = campaignesArray.map((campaign) =>
          createData(
            campaign.id ?? -1,
            campaign.campaignName,
            campaign.keywords,
            campaign.bidAmount,
            campaign.campaignFund,
            campaign.status ? "on" : "off",
            campaign.town,
            campaign.radius
          )
        );
        setRows(transformedRows);
      }
    })();
  }, []);
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell align="center">Campaign name</StyledTableCell>
            <StyledTableCell align="center">Keywords</StyledTableCell>
            <StyledTableCell align="center">Bid amount</StyledTableCell>
            <StyledTableCell align="center">Campaign fund</StyledTableCell>
            <StyledTableCell align="center">Status</StyledTableCell>
            <StyledTableCell align="center">Town</StyledTableCell>
            <StyledTableCell align="center">Radius&nbsp;(km)</StyledTableCell>
            <StyledTableCell align="center">Options</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <StyledTableRow key={row.campaignName}>
              <StyledTableCell component="th" scope="row" align="center">
                {row.campaignName}
              </StyledTableCell>
              <StyledTableCell align="center">
                <div className="flex flex-wrap gap-2">
                  {row.keywords.map((keyword: string, index: number) => (
                    <span
                      key={index}
                      className="bg-blue-100 text-blue-800 text-xs font-semibold mr-2 px-2.5 py-0.5 rounded dark:bg-blue-200 dark:text-blue-800"
                    >
                      {keyword}
                    </span>
                  ))}
                </div>
              </StyledTableCell>
              <StyledTableCell align="center">{row.bidAmount}</StyledTableCell>
              <StyledTableCell align="center">
                {row.campaignFund}
              </StyledTableCell>
              <StyledTableCell align="center">{row.status}</StyledTableCell>
              <StyledTableCell align="center">{row.town}</StyledTableCell>
              <StyledTableCell align="center">{row.radius}</StyledTableCell>
              <StyledTableCell align="center" className="flex space-x-4">
                <Button variant="contained" color="error" className="w-[25%]">
                  Delete
                </Button>
                <Button variant="contained" color="warning" className="w-[25%]">
                  Edit
                </Button>
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
