/**
 * This class is used to create tabs for the user to navigate through the website.
 */
//calls the function openTap
openTap();

//makes the tabs clickable and opens the content of the tab
//uses an event listener to listen for a click
//makes a default tab open when the page is loaded and clicked
function openTap(evt, tabName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for(i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for(i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}
tablink.addEventListener("click", openTap);

document.getElementById("defaultOpen").click();
