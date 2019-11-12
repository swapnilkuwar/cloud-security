<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="google-signin-client_id" content="991464079057-aukil8lcsq9bkn8th7pl86ovhgr323to.apps.googleusercontent.com">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Logout</title>
</head>
<body>
<h1>You are successfully logged out!!!</h1>
<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
<script type="text/javascript">
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }

  function onLoad() {
    gapi.load('auth2', function() {
      gapi.auth2.init().then(() => { signOut(); });
    });
  }
</script>
</body>
</html>