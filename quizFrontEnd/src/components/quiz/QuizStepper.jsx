import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { getAllQuestions, getSubjects } from "../../../utils/QuizService.jsx"

const QuizStepper = () => {
	const [currentStep, setCurrentStep] = useState(1)
	const [selectedSubject, setSelectedSubject] = useState("")
	const [selectedNumQuestions, setSelectedNumQuestions] = useState("")
	const [subjects, setSubjects] = useState([])
	const [maxQuestions, setMaxQuestions] = useState(0)
	const [errorMessage, setErrorMessage] = useState("")
	const navigate = useNavigate()

	useEffect(() => {
		fetchSubjectData()
	}, [])

	useEffect(() => {
		if (selectedSubject) {
			checkAvailableQuestions(selectedSubject)
		}
	}, [selectedSubject])

	const fetchSubjectData = async () => {
		try {
			const subjectsData = await getSubjects()
			setSubjects(subjectsData)
		} catch (error) {
			console.error(error)
		}
	}

	const checkAvailableQuestions = async (subject) => {
		try {
			const allQuestions = await getAllQuestions()
			const filtered = allQuestions.filter(q => q.subject === subject)
			setMaxQuestions(filtered.length)
		} catch (error) {
			console.error("Erreur lors de la récupération des questions :", error)
		}
	}

	const handleNext = () => {
		const num = parseInt(selectedNumQuestions)

		if (currentStep === 3) {
			if (!selectedSubject || !selectedNumQuestions) {
				alert("Veuillez sélectionner un sujet et un nombre de questions.")
				return
			}
			if (isNaN(num) || num <= 0) {
				setErrorMessage("Le nombre de questions doit être supérieur à zéro.")
				return
			}
			if (num > maxQuestions) {
				setErrorMessage(`Il n'y a que ${maxQuestions} question(s) disponibles pour ce sujet.`)
				return
			}
			setErrorMessage("")
			navigate("/take-quiz", { state: { selectedNumQuestions, selectedSubject } })
		} else {
			setErrorMessage("")
			setCurrentStep((prevStep) => prevStep + 1)
		}
	}

	const handlePrevious = () => {
		setErrorMessage("")
		setCurrentStep((prevStep) => prevStep - 1)
	}

	const handleSubjectChange = (event) => {
		setSelectedSubject(event.target.value)
		setSelectedNumQuestions("")
		setErrorMessage("")
	}

	const handleNumQuestionsChange = (event) => {
		setSelectedNumQuestions(event.target.value)
		setErrorMessage("")
	}

	const renderStepContent = () => {
		switch (currentStep) {
			case 1:
				return (
					<div>
						<h3 className="text-info mb-2">Je veux passer un quiz sur :</h3>
						<select
							className="form-select"
							value={selectedSubject}
							onChange={handleSubjectChange}>
							<option value="">Sélectionnez un sujet</option>
							{subjects.map((subject) => (
								<option key={subject} value={subject}>
									{subject}
								</option>
							))}
						</select>
					</div>
				)
			case 2:
				return (
					<div>
						<h4 className="text-info mb-2">Combien de questions souhaitez-vous tenter ?</h4>
						<input
							type="number"
							className="form-control"
							value={selectedNumQuestions}
							onChange={handleNumQuestionsChange}
							placeholder={`Max : ${maxQuestions}`}
							min="1"
						/>
						{errorMessage && (
							<p className="text-danger mt-2">{errorMessage}</p>
						)}
					</div>
				)
			case 3:
				return (
					<div>
						<h2>Confirmation</h2>
						<p>Sujet : {selectedSubject}</p>
						<p>Nombre de questions : {selectedNumQuestions}</p>
					</div>
				)
			default:
				return null
		}
	}

	const renderProgressBar = () => {
		const progress = currentStep === 3 ? 100 : ((currentStep - 1) / 2) * 100
		return (
			<div className="progress">
				<div
					className="progress-bar"
					role="progressbar"
					style={{ width: `${progress}%` }}
					aria-valuenow={progress}>
					Étape {currentStep}
				</div>
			</div>
		)
	}

	return (
		<section className="container mt-5">
			<h2 className="mb-4">Passer un Quiz !</h2>
			{renderProgressBar()}
			<div className="card">
				<div className="card-body">
					{renderStepContent()}
					<div className="d-flex justify-content-between mt-4">
						{currentStep > 1 && (
							<button className="btn btn-primary" onClick={handlePrevious}>
								Précédent
							</button>
						)}
						{currentStep < 3 && (
							<button
								className="btn btn-primary"
								onClick={handleNext}
								disabled={
									(currentStep === 1 && !selectedSubject) ||
									(currentStep === 2 &&
										(!selectedNumQuestions ||
											isNaN(selectedNumQuestions) ||
											parseInt(selectedNumQuestions) <= 0 ||
											parseInt(selectedNumQuestions) > maxQuestions))
								}>
								Suivant
							</button>
						)}
						{currentStep === 3 && (
							<button className="btn btn-success" onClick={handleNext}>
								Démarrer le quiz
							</button>
						)}
					</div>
				</div>
			</div>
		</section>
	)
}

export default QuizStepper
