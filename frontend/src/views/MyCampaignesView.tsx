import Paper from "@mui/material/Paper";
import { styled } from "@mui/material/styles";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";

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

// function createData(
//   campaignName: string,
//   keywords: string[],
//   bidAmount: number,
//   campaignFund: number,
//   status: "on" | "off",
//   town: string,
//   radius: number
// ) {
//   return { campaignName, keywords, bidAmount, campaignFund, status, town, radius };
// }

const rows: any[] = [
];

export default function MyCampaignesView() {
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>Campaign name</StyledTableCell>
            <StyledTableCell align="right">Keywords</StyledTableCell>
            <StyledTableCell align="right">Bid amount</StyledTableCell>
            <StyledTableCell align="right">Campaign fund</StyledTableCell>
            <StyledTableCell align="right">Status</StyledTableCell>
            <StyledTableCell align="right">Town</StyledTableCell>
            <StyledTableCell align="right">Radius&nbsp;(km)</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <StyledTableRow key={row.campaignName}>
              <StyledTableCell component="th" scope="row">
                {row.campaignName}
              </StyledTableCell>
              <StyledTableCell align="right">{row.keywords}</StyledTableCell>
              <StyledTableCell align="right">{row.bidAmount}</StyledTableCell>
              <StyledTableCell align="right">{row.campaignFund}</StyledTableCell>
              <StyledTableCell align="right">{row.status}</StyledTableCell>
              <StyledTableCell align="right">{row.town}</StyledTableCell>
              <StyledTableCell align="right">{row.radius}</StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
