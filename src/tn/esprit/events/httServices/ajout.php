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

$allowedExts = array("gif", "jpeg", "jpg", "png");
$temp = explode(".", $_FILES["file"]["name"]);
$extension = end($temp);
if ((($_FILES["file"]["type"] == "image/gif") || ($_FILES["file"]["type"] == "image/jpeg") || ($_FILES["file"]["type"] == "image/jpg") || ($_FILES["file"]["type"] == "image/pjpeg") || ($_FILES["file"]["type"] == "image/x-png") || ($_FILES["file"]["type"] == "image/png")) && ($_FILES["file"]["size"] < 5000000) && in_array($extension, $allowedExts)) {
    if ($_FILES["file"]["error"] > 0) {
        $named_array = array("Response" => array(array("Status" => "error")));
        echo json_encode($named_array);
    } else {
        move_uploaded_file($_FILES["file"]["tmp_name"],"C:\wamp64\www\PIDEV - Copy\web\bundles\blog/template/images/". $_FILES["file"]["name"]);
		
        $Path = $_FILES["file"]["name"];
		echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" ; 
		echo $Path ; 
        $named_array = array("Response" => array(array("Status" => "ok")));
        echo json_encode($named_array);
    }
} else {
    $named_array = array("Response" => array(array("Status" => "invalid")));
    echo json_encode($named_array);
}

$user=$_GET['utilisateur_id'];
echo $user ; 
$nom=$_GET['nom'];
$date=$_GET['date'];
$adresse=$_GET['adresse'];
$sql = "INSERT INTO evenements (nom,dateF,adresse,brochure,utilisateur_id)
VALUES ( '$nom','$date','$adresse','$Path','$user')";
if (mysqli_query($conn, $sql)) {
    echo "success";
	echo $Path;
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}


?>
