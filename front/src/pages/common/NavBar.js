/*eslint-disable*/
import {Container, Nav, Navbar} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import {useQuery} from "react-query"
import {useEffect, useState} from "react";
import Button from "react-bootstrap/Button";
import axios from "axios";
import {useDispatch, useSelector} from "react-redux";
import {setUser,logOutUser} from "../../store/user";
import logo from "../../img/logo.png"
const NavBar = () => {

    let navigator = useNavigate();
    let user = useSelector((state) => state.user);
    let dispatch = useDispatch();

    useEffect(()=>{
        if(JSON.parse(localStorage.getItem('user'))){
            dispatch(setUser(JSON.parse(localStorage.getItem('user'))));
        }
    },[])

    let result = useQuery('test',()=>
         axios.get(`https://codingapple1.github.io/userdata.json`)
         .then((res) => {  return res.data} ),
        {staleTime:2000}
    )

    const logOut = () => {
        localStorage.removeItem('user')
        dispatch(logOutUser());
        navigator("/");
    }



    return(
        <>
            <Navbar expand="lg" className="bg-body-tertiary" bg="dark" data-bs-theme="dark">
                <Container>

                        <Navbar.Brand onClick={() => {navigator('/')}} style={{ cursor:"pointer"}} >SALCHO</Navbar.Brand>
                        {/* <Navbar.Brand onClick={() => {navigator('/')}} >
                                    <img src={logo} style={{ cursor:"pointer",height: "55px", filter: "opacity(0.5) drop-shadow(0 0 0 #FFFFFF)"}}/>
                        </Navbar.Brand>*/}

                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                          {/*  <Nav.Link onClick={() => {navigator('/')}}>Home</Nav.Link>*/}
                            
                        </Nav>
                        {
                            user.nickName ?
                            <Nav>
                                <Nav.Link >
                                    <strong className="m-2" onClick={() => navigator('/mypage')}>{user.nickName} 님</strong>
                                    <button variant="light"  className="logOut" onClick={() => {logOut()}} >
                                        logOut
                                    </button>
                                </Nav.Link>
                            </Nav>
                        :
                            <Nav className="justify-content-end" >
                                <Nav.Link onClick={() => {navigator('/signin')}}>sign in</Nav.Link>
                                <Nav.Link onClick={() => {navigator('/signup')}}>sign up</Nav.Link>
                            </Nav>
                        }
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    )
}
{/*     <Nav className="me-auto text-white">
                    {result.isLoading && "loding..." }
                    {result.error && "error" }
                    {result.data && result.data.name }
                </Nav>*/}
export default NavBar;