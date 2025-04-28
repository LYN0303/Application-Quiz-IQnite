// eslint-disable-next-line no-unused-vars
import React from "react"

// eslint-disable-next-line react/prop-types
const AnswerOptions = ({ question, isChecked, handleAnswerChange, handleCheckboxChange }) => {
	if (!question) {
		return (
			<div>
				No questions available, <br /> you may try agian by reducing your requested number of
				questions on this topic
			</div>
		)
	}

	// eslint-disable-next-line react/prop-types
	const { id, questionType, choices } = question

	if (questionType === "single") {
		return (
			<div>
				{/* eslint-disable-next-line react/prop-types */}
				{choices.sort().map((choice) => (
					<div key={choice} className="form-check mb-3">
						<input
							className="form-check-input"
							type="radio"
							id={choice}
							/* eslint-disable-next-line react/prop-types */
							name={question.id}
							value={choice}
							/* eslint-disable-next-line react/prop-types */
							checked={isChecked(question.id, choice)}
							onChange={() => handleAnswerChange(id, choice)}
						/>
						<label htmlFor={choice} className="form-check-label ms-2">
							{choice}
						</label>
					</div>
				))}
			</div>
		)
	} else if (questionType === "multiple") {
		return (
			<div>
				{/* eslint-disable-next-line react/prop-types */}
				{choices.sort().map((choice) => (
					<div key={choice} className="form-check mb-3">
						<input
							className="form-check-input"
							type="checkbox"
							id={choice}
							/* eslint-disable-next-line react/prop-types */
							name={question.id}
							value={choice}
							/* eslint-disable-next-line react/prop-types */
							checked={isChecked(question.id, choice)}
							onChange={() => handleCheckboxChange(id, choice)}
						/>
						<label htmlFor={choice} className="form-check-label ms-2">
							{choice}
						</label>
					</div>
				))}
			</div>
		)
	} else {
		return null
	}
}

export default AnswerOptions
