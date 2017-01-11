<!DOCTYPE HTML>
<html>
<head>
    <head>
    	<!-- meta character set -->
        <meta charset="utf-8">
		<!-- Always force latest IE rendering engine or request Chrome Frame -->
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Walk simulator - Sign up<?php echo $uid ?>!</title>		
		<!-- Meta Description -->
		<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
        <meta name="description" content="Walk simulator is a game where the only goal is to walk as far as possible.">
        <meta name="keywords" content="walk, simulator, game, alpha">
        <meta name="author" content="Jelle Glebbeek">
		
		<!-- Mobile Specific Meta -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
		
		<!-- CSS
		================================================== -->
		
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700' rel='stylesheet' type='text/css'>
		
		<!-- Fontawesome Icon font -->
        <link rel="stylesheet" href="css/font-awesome.min.css">
		<!-- bootstrap.min -->
        <link rel="stylesheet" href="css/jquery.fancybox.css">
		<!-- bootstrap.min -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
		<!-- bootstrap.min -->
        <link rel="stylesheet" href="css/owl.carousel.css">
		<!-- bootstrap.min -->
        <link rel="stylesheet" href="css/slit-slider.css">
		<!-- bootstrap.min -->
        <link rel="stylesheet" href="css/animate.css">
		<!-- Main Stylesheet -->
        <link rel="stylesheet" href="css/main.css">

		<!-- Modernizer Script for old Browsers -->
        <script src="js/modernizr-2.6.2.min.js"></script>
</head>
<body id="body">
		<div id="preloader">
            <div class="loder-box">
            	<div class="battery"></div>
            </div>
		</div>
		
 <header id="navigation" class="navbar-inverse navbar-fixed-top animated-header">
            <div class="container">
                <div class="navbar-header">
                    <!-- responsive nav button -->
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
                    </button>
					<!-- /responsive nav button -->
					
					<!-- logo -->
					<h1 class="navbar-brand">
						<a href="index.html">Walk simulator</a>
					</h1>
					<!-- /logo -->
                </div>

				<!-- main nav -->
                <nav class="collapse navbar-collapse navbar-right" role="navigation">
                    <ul id="nav" class="nav navbar-nav">
                        <li><button onclick="location.href='index.html'" class="btn btn-effect btn-blue" style="border: none; background: transparent;"><b>Home</b></button></li>

                    </ul>
                </nav>
				<!-- /main nav -->
				
            </div>
        </header>
		
		
		<main class="site-content" role="main">
		
		
					<section id="about" >
				<div class="container">
					<div class="row">
						<div class="col-md-7 col-md-offset-1 wow animated fadeInRight">
							<div class="welcome-block"><br><br><br><br><br><br><br><br>	
                                 <h3>Walk Simulator - Sign up</h3>							 
						     	 <div class="message-body">
								 <form action="usersignupproces.php" method="POST">
								 	<input type="text" name="uid" class="form-control" placeholder="Username" required><br>
									<input type="password" name="pwd" class="form-control" placeholder="Password" required><br>
									<input type="email" name="email" class="form-control" placeholder="E-mail" required><br>
									<input type="number" name="code" class="form-control" placeholder="Your alpha acces code" oninput="javascript: if (this.value.length > 6) this.value = this.value.slice(0, this.maxLength);" required><br>
									<span>No! We won't send you any E-Mails if you don't contact us first.</span><br>
                                    <span>Dont have an alpha acces code? <a href="alphaprogram.html" style="color: #cccccc;">Get one here!</a></span><br><br>									
									<button type="submit" id="submit" class="btn btn-effect btn-blue"><b>Sign up</b></button>
								 <form> 
								 <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
						     	 </div>
						    </div>
						</div>
					</div>
				</div>
			</section>
		
		</main>
		
		<footer id="footer">
			<div class="container">
				<div class="row text-center">
					<div class="footer-content">
						<div class="wow animated fadeInDown">
						</div>
						<p>Copyright &copy; 2016-2017 Designed and Developed By<a> Jely2002</a> </p>
					</div>
				</div>
			</div>
		</footer>
		
<script src="js/jquery-1.11.1.min.js"></script>
		<!-- Twitter Bootstrap -->
        <script src="js/bootstrap.min.js"></script>
		<!-- Single Page Nav -->
        <script src="js/jquery.singlePageNav.min.js"></script>
		<!-- jquery.fancybox.pack -->
        <script src="js/jquery.fancybox.pack.js"></script>
		<!-- Google Map API -->
		<script src="http://maps.google.com/maps/api/js?sensor=false"></script>
		<!-- Owl Carousel -->
        <script src="js/owl.carousel.min.js"></script>
        <!-- jquery easing -->
        <script src="js/jquery.easing.min.js"></script>
        <!-- Fullscreen slider -->
        <script src="js/jquery.slitslider.js"></script>
        <script src="js/jquery.ba-cond.min.js"></script>
		<!-- onscroll animation -->
        <script src="js/wow.min.js"></script>
		<!-- Custom Functions -->
        <script src="js/main.js"></script>
</body>
</html>