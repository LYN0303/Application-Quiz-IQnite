import { Link } from 'react-router-dom'

const Admin = () => {
	return (
		<section className="container">
			<h2 className="mt-5">Espace Admin</h2>
			<hr />
			<nav className="nav flex-column">
				<Link to={"/create-quiz"} className="nav-link">
					Créer un nouveau quiz
				</Link>
				<Link to={"/all-quizzes"} className="nav-link">
					Gérer les quiz existants
				</Link>
			</nav>
		</section>
	)
}

export default Admin
