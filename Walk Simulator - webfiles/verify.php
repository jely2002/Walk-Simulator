<?php
include 'dbh.php';

$uid = $_GET['uid'];
$pwd = $_GET['pwd'];

    
$result = $conn->query("SELECT id FROM userdb WHERE uid = '$uid'");
if($result->num_rows == 0) {
	echo 'denied';
} else {
$sql = "SELECT pwd FROM userdb WHERE uid = '$uid'";
$result1 = mysqli_query($conn, $sql);
if (mysqli_num_rows($result1) > 0) {
    while($row = mysqli_fetch_assoc($result1)) {
        if($row['pwd'] == $pwd){
			echo 'granted';
		} else {
			echo 'denied';
		}
    } 
}
}
?>