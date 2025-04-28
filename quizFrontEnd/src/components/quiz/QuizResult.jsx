import { useLocation } from "react-router-dom"

const QuizResult = () => {
	const location = useLocation()
	const { quizQuestions, totalScores } = location.state
	const numQuestions = quizQuestions.length
	const percentage = Math.round((totalScores / numQuestions) * 100)

	const handleRetakeQuiz = () => {
		alert("Oups ! Cette fonctionnalité n'a pas été implémentée !")
	}

	return (
		<section className="container mt-5">
			<h3>Résumé de vos résultats de quiz</h3>
			<hr />
			<h5 className="text-info">
				Vous avez répondu correctement à {totalScores} questions sur {numQuestions}.
			</h5>
			<p>Votre score total est de {percentage}%.</p>

			<button className="btn btn-primary btn-sm" onClick={handleRetakeQuiz}>
				Repasser ce quiz
			</button>
		</section>
	)
}

export default QuizResult
