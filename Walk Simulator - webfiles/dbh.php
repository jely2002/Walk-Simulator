<?php

$conn = mysqli_connect("localhost", "root", "", "userlogin");

if (!$conn) {
	die("Connection failed, no error visible..");
}