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
				minHeight: "100vh",
				display: "flex",
				flexDirection: "column",
				alignItems: "center",
				justifyContent: "center",
				padding: "20px",
				color: "#fff",
				textShadow: "2px 2px 4px rgba(0,0,0,0.7)",
				textAlign: "center",
			}}>
			<h2
				style={{
					fontSize: "3rem",
					fontWeight: "700",
					color: "#fff",
					textShadow: "2px 2px 8px rgba(0,0,0,0.7)",
					marginBottom: "2rem",
					maxWidth: "800px",
				}}>
				Bienvenue sur notre application de quiz IQnite
			</h2>
			<div style={{ display: "flex", gap: "20px" }}>
				<Link
					to="/admin"
					style={{
						backgroundColor: "#0b30ba",
						color: "#fff",
						borderRadius: "5px",
						padding: "12px 24px",
						fontSize: "1.2rem",
						textDecoration: "none",
						transition: "transform 0.2s",
					}}
					onMouseEnter={(e) => (e.currentTarget.style.transform = "scale(1.05)")}
					onMouseLeave={(e) => (e.currentTarget.style.transform = "scale(1)")}>
					Espace Admin
				</Link>
				<Link
					to="/quiz-stepper"
					style={{
						backgroundColor: "#0b30ba",
						color: "#fff",
						borderRadius: "5px",
						padding: "12px 24px",
						fontSize: "1.2rem",
						textDecoration: "none",
						transition: "transform 0.2s",
					}}
					onMouseEnter={(e) => (e.currentTarget.style.transform = "scale(1.05)")}
					onMouseLeave={(e) => (e.currentTarget.style.transform = "scale(1)")}>
					Passer un quiz
				</Link>
			</div>
		</main>
	)
}

export default Home