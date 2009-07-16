function display_mode(str)
{    
    if(str=="list"){
    document.getElementById('listmodel').style.display="block"; 
    document.getElementById('gridmodel').style.display="none"; 
    document.getElementById('textmodel').style.display="none"; 
    }
    else if(str=="grid"){
    document.getElementById('listmodel').style.display="none"; 
    document.getElementById('gridmodel').style.display="block"; 
    document.getElementById('textmodel').style.display="none"; 
    }
    else{
    document.getElementById('listmodel').style.display="none"; 
    document.getElementById('gridmodel').style.display="none"; 
    document.getElementById('textmodel').style.display="block"; 
    }    
}