import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { getSubjects } from "../../../utils/QuizService.jsx"

const QuizStepper = () => {
	const [currentStep, setCurrentStep] = useState(1)
	const [selectedSubject, setSelectedSubject] = useState("")
	const [selectedNumQuestions, setSelectedNumQuestions] = useState("")
	const [subjects, setSubjects] = useState([])
	const navigate = useNavigate()

	useEffect(() => {
		fetchSubjectData()
	}, [])

	const fetchSubjectData = async () => {
		try {
			const subjectsData = await getSubjects()
			setSubjects(subjectsData)
		} catch (error) {
			console.error(error)
		}
	}

	const handleNext = () => {
		if (currentStep === 3) {
			if (selectedSubject && selectedNumQuestions) {
				navigate("/take-quiz", { state: { selectedNumQuestions, selectedSubject } })
			} else {
				alert("Veuillez sélectionner un sujet et un nombre de questions.")
			}
		} else {
			setCurrentStep((prevStep) => prevStep + 1)
		}
	}

	const handlePrevious = () => {
		setCurrentStep((prevStep) => prevStep - 1)
	}

	const handleSubjectChange = (event) => {
		setSelectedSubject(event.target.value)
	}

	const handleNumQuestionsChange = (event) => {
		setSelectedNumQuestions(event.target.value)
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
							placeholder="Entrez le nombre de questions"
						/>
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
									(currentStep === 2 && !selectedNumQuestions)
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
