<?php

include 'dbh.php';

$uid = $_GET['uid'];
$dist = $_GET['dist'];

if ($uid == null) {
	exit();
}
if ($dist == null) {
	exit();
}

$result = $conn->query("SELECT id FROM userdb WHERE uid = '$uid'");
if($result->num_rows == 0) {
     echo "Ading all values to table.";
	 $sql = "INSERT INTO userdb (uid, dist)
     VALUES ('$uid', '$dist')";
	 $result = mysqli_query($conn, $sql);
	 
} else {
    $result1 = $conn->query("SELECT * FROM userdb WHERE uid = '$uid'");
	$resultarray = array();
while($row = mysqli_fetch_array($result1))
{
    $result2 = $row["dist"];
}
$updatedDist = $result2 + $dist;

$updatesql = "UPDATE userdb SET dist='$updatedDist' WHERE uid='$uid'";

if (mysqli_query($conn, $updatesql)) {
    echo "Record updated successfully";
} else {
    echo "Error updating record..";
}
}
?>