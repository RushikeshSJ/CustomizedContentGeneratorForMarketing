import React from 'react';
import '../styles/components.css';

const GeneratedContent = ({ content }) => (
  <div className="generated-content">
    <h2>Generated Content</h2>
    {content ? <p>{content}</p> : <p>No content generated yet! Please submit a request.</p>}
  </div>
);

export default GeneratedContent;
