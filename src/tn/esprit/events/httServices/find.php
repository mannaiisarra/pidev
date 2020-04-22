<?php



$servername = "localhost";
$username = "root";
$password = "";
$dbname = "bonplan";
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$id=$_GET['id'] ; 
$return_arr = array();
$sql = "SELECT * FROM evenements WHERE id =$id ";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
       $row_array['id'] = $row['id'];
       $row_array['nom'] = $row['nom'];
       $row_array['dateF'] = $row['dateF'];
	   $row_array['adresse'] = $row['adresse'];
	   $row_array['brochure'] = $row['brochure'];
    array_push($return_arr,$row_array);
    }
} 
echo json_encode($return_arr);





?>