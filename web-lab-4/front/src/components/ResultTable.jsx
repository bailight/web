import {Table, TableCell, TableHead, TableRow} from "@mui/material";

const ResultTable = ({results}) => {

    return (
        <Table id="resultTable">
            <TableHead>
                <TableRow>
                    <TableCell sx={{textAlign: 'center'}}>X</TableCell>
                    <TableCell sx={{textAlign: 'center'}}>Y</TableCell>
                    <TableCell sx={{textAlign: 'center'}}>R</TableCell>
                    <TableCell sx={{textAlign: 'center'}}>Result</TableCell>
                </TableRow>
            </TableHead>
            <tbody>
            {results.map((result) => (
                <TableRow key={result.id}>
                    <TableCell sx={{textAlign: 'center'}}>{result.x}</TableCell>
                    <TableCell sx={{textAlign: 'center'}}>{result.y}</TableCell>
                    <TableCell sx={{textAlign: 'center'}}>{result.r}</TableCell>
                    <TableCell sx={{textAlign: 'center'}}>{result.is_check ? "True" : "False"}</TableCell>
                </TableRow>
            ))}
            </tbody>
        </Table>
    );
};

export default ResultTable;