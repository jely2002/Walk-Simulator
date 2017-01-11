<?php
include 'dbh.php';

$uid = $_POST['uid'];
$pwd = $_POST['pwd'];
$email = $_POST['email'];
$code = $_POST['code'];
$dist = 1;

$sql = "SELECT code FROM accescodes";
$result1 = mysqli_query($conn, $sql);
if (mysqli_num_rows($result1) > 0) {
    while($row = mysqli_fetch_assoc($result1)) {
        if($row['code'] == $code){
			$granted = true;
		}
    }
	if ($granted == true){
	 $result = $conn->query("SELECT id FROM userdb WHERE uid = '$uid'");
     if($result->num_rows == 0) {
	 $sql = "INSERT INTO userdb (uid, dist, pwd, email)
     VALUES ('$uid', '$dist', '$pwd', '$email')";
	 $result = mysqli_query($conn, $sql);
	 $sql = "DELETE FROM accescodes WHERE code = '$code'";
	 $result = mysqli_query($conn, $sql);
     header("Location:signupsucces.php");
     exit();	 
} else {
	header("Location:index.html");
	exit();
}
	} else {
		header("Location:index.html");
		exit();
	}
} else {
    echo "Error! Database failure.. Please contact the webmaster.";
}

//$result = $conn->query("SELECT id FROM userdb WHERE uid = '$uid'");
//if($result->num_rows == 0) {
//	 $sql = "INSERT INTO userdb (uid, dist, pwd, email)
//     VALUES ('$uid', '$dist')";
//	 $result = mysqli_query($conn, $sql);
	 
//} else {
	
//}

?>