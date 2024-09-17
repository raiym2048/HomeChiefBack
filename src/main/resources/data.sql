-- Migration script for inserting default values into RequestStatus table

-- Ensure the table exists
INSERT INTO request_status (id, status) VALUES
                                            (uuid_generate_v4(), 'not verified'),
                                            (uuid_generate_v4(), 'not accepted'),
                                            (uuid_generate_v4(), 'accepted'),
                                            (uuid_generate_v4(), 'blocked');
