import React from "react"
import { Link } from "react-router-dom"
import backgroundImage from "../assets/quizBackground.png"

const Home = () => {
	return (
		<main
			style={{
				backgroundImage: `url(${backgroundImage})`,
				backgroundSize: "cover",
				backgroundPosition: "center",
				backgroundRepeat: "no-repeat",
				width: "100%",
				minHeight: "100vh",
				overflow: "hidden",
				display: "flex",
				flexDirection: "column",
				alignItems: "center",
				paddingTop: "10vh",
				color: "#fff",
				textShadow: "2px 2px 4px rgba(0,0,0,0.7)",
			}}>
			<h2
				style={{
					fontSize: "3rem",
					fontWeight: "700",
					color: "#fff",
					textShadow: "2px 2px 8px rgba(0,0,0,0.7)",
				}}
				className="mb-4">
				Bienvenue sur l'application de quiz IQnite
			</h2>
			<div className="d-flex justify-content-center">
				<Link
					to="/admin"
					className="btn"
					style={{
						backgroundColor: "#4b0082",
						color: "#fff",
						borderRadius: "5px",
						padding: "10px 20px",
						fontSize: "1.2rem",
						margin: "0 10px",
					}}>
					Espace Admin
				</Link>
				<Link
					to="/quiz-stepper"
					className="btn"
					style={{
						backgroundColor: "#4b0082",
						color: "#fff",
						borderRadius: "5px",
						padding: "10px 20px",
						fontSize: "1.2rem",
						margin: "0 10px",
					}}>
					Passer un quiz
				</Link>
			</div>
		</main>
	)
}

export default Home
