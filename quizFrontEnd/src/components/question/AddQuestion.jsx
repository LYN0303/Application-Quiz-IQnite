import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import { createQuestion, getSubjects } from "../../../utils/QuizService.jsx"

const AddQuestion = () => {
	const [question, setQuestionText] = useState("")
	const [questionType, setQuestionType] = useState("single")
	const [choices, setChoices] = useState([""])
	const [correctAnswers, setCorrectAnswers] = useState([])
	const [subject, setSubject] = useState("")
	const [newSubject, setNewSubject] = useState("")
	const [subjectOptions, setSubjectOptions] = useState([""])

	const [errors, setErrors] = useState({
		question: "",
		choices: "",
		subject: "",
		newSubject: "",
		correctAnswers: ""
	})

	useEffect(() => {
		fetchSubjects()
	}, [])

	const fetchSubjects = async () => {
		try {
			const subjectsData = await getSubjects()
			setSubjectOptions(subjectsData)
		} catch (error) {
			console.error(error)
		}
	}

	const handleAddChoice = () => {
		const lastChoice = choices[choices.length - 1]
		const lastChoiceLetter = lastChoice ? lastChoice.charAt(0) : "A"
		const newChoiceLetter = String.fromCharCode(lastChoiceLetter.charCodeAt(0) + 1)
		const newChoice = `${newChoiceLetter}.`
		setChoices([...choices, newChoice])
	}

	const handleRemoveChoice = (index) => {
		setChoices(choices.filter((choice, i) => i !== index))
	}

	const handleChoiceChange = (index, value) => {
		setChoices(choices.map((choice, i) => (i === index ? value : choice)))
	}

	const handleCorrectAnswerChange = (event) => {
		const value = event.target.value
		if (correctAnswers.includes(value)) {
			setCorrectAnswers(correctAnswers.filter(answer => answer !== value))
		} else {
			setCorrectAnswers([...correctAnswers, value])
		}
	}

	const validateForm = () => {
		const newErrors = {
			question: question ? "" : "La question est requise.",
			choices: choices.every(choice => choice.trim()) ? "" : "Les choix sont requis.",
			subject: subject ? "" : "Le sujet est requis.",
			newSubject: subject === "New" && !newSubject.trim() ? "Le nom de la nouvelle matière est requis." : "",
			correctAnswers: correctAnswers.length === 0 ? "Veuillez sélectionner au moins une réponse correcte." : ""
		}

		setErrors(newErrors)
		return !Object.values(newErrors).some((error) => error)
	}

	const handleSubmit = async (e) => {
		e.preventDefault()

		if (!validateForm()) return

		try {
			const result = {
				question,
				questionType,
				choices,
				correctAnswers,
				subject: subject === "New" ? newSubject.trim() : subject
			}

			await createQuestion(result)

			setQuestionText("")
			setQuestionType("single")
			setChoices([""])
			setCorrectAnswers([])
			setSubject("")
			setNewSubject("")
			setErrors({})
		} catch (error) {
			console.error(error)
		}
	}

	const handleAddSubject = () => {
		if (newSubject.trim() !== "") {
			setSubject(newSubject.trim())
			setSubjectOptions([...subjectOptions, newSubject.trim()])
			setNewSubject("")
		}
	}

	return (
		<div className="container">
			<div className="row justify-content-center">
				<div className="col-md-6 mt-5">
					<div className="card">
						<div className="card-header">
							<h5 className="card-title">Ajouter de nouvelles questions</h5>
						</div>
						<div className="card-body">
							<form onSubmit={handleSubmit} className="p-2">
								<div className="mb-3">
									<label htmlFor="subject" className="form-label text-info">
										Sélectionner une matière
									</label>
									<select
										id="subject"
										value={subject}
										onChange={(e) => setSubject(e.target.value)}
										className="form-control">
										<option value="">--Sélectionner une matière--</option>
										<option value="New">Ajouter une nouvelle</option>
										{subjectOptions.map((option) => (
											<option key={option} value={option}>
												{option}
											</option>
										))}
									</select>
									{errors.subject && <div className="text-danger">{errors.subject}</div>}
								</div>

								{subject === "New" && (
									<div className="mb-3">
										<label htmlFor="new-subject" className="form-label text-info">
											Ajouter une nouvelle matière
										</label>
										<input
											type="text"
											id="new-subject"
											value={newSubject}
											onChange={(event) => setNewSubject(event.target.value)}
											className="form-control"
										/>
										{errors.newSubject && <div className="text-danger">{errors.newSubject}</div>}
										<button
											type="button"
											onClick={handleAddSubject}
											className="btn btn-outline-primary mt-2">
											Ajouter la matière
										</button>
									</div>
								)}

								<div className="mb-3">
									<label htmlFor="question-text" className="form-label text-info">
										Question
									</label>
									<textarea
										className="form-control"
										rows={4}
										value={question}
										onChange={(e) => setQuestionText(e.target.value)}></textarea>
									{errors.question && <div className="text-danger">{errors.question}</div>}
								</div>

								<div className="mb-3">
									<label htmlFor="question-type" className="form-label text-info">
										Type de question
									</label>
									<select
										id="question-type"
										value={questionType}
										onChange={(event) => setQuestionType(event.target.value)}
										className="form-control">
										<option value="single">Réponse unique</option>
										<option value="multiple">Réponse multiple</option>
									</select>
								</div>

								<div className="mb-3">
									<label htmlFor="choices" className="form-label text-primary">
										Choix
									</label>
									{choices.map((choice, index) => (
										<div key={index} className="input-group mb-3">
											<input
												type="text"
												value={choice}
												onChange={(e) => handleChoiceChange(index, e.target.value)}
												className="form-control"
											/>
											<button
												type="button"
												onClick={() => handleRemoveChoice(index)}
												className="btn btn-outline-danger">
												Supprimer
											</button>
										</div>
									))}
									<button
										type="button"
										onClick={handleAddChoice}
										className="btn btn-outline-primary">
										Ajouter un choix
									</button>
									{errors.choices && <div className="text-danger">{errors.choices}</div>}
								</div>

								<div className="mb-3">
									<label htmlFor="answer" className="form-label text-success">
										{questionType === "single" ? "Réponse correcte" : "Réponse(s) correcte(s)"}
									</label>
									{questionType === "single" ? (
										<select
											id="answer"
											className="form-control"
											value={correctAnswers[0] || ""}
											onChange={(e) => setCorrectAnswers([e.target.value])}
										>
											<option value="">Sélectionner une réponse</option>
											{choices.map((choice, index) => (
												<option key={index} value={choice}>
													{choice}
												</option>
											))}
										</select>
									) : (
										<>
											{choices.map((choice, index) => (
												<div key={index} className="form-check">
													<input
														type="checkbox"
														className="form-check-input"
														value={choice}
														onChange={handleCorrectAnswerChange}
														checked={correctAnswers.includes(choice)}
													/>
													<label className="form-check-label">{choice}</label>
												</div>
											))}
										</>
									)}
									{errors.correctAnswers && <div className="text-danger">{errors.correctAnswers}</div>}
								</div>

								<div className="btn-group">
									<button type="submit" className="btn btn-outline-success mr-2">
										Enregistrer la question
									</button>
									<Link to={"/all-quizzes"} className="btn btn-outline-primary ml-2">
										Retour aux questions existantes
									</Link>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	)
}

export default AddQuestion
